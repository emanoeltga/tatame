import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

/** Interceptor que anexa Authorization e redireciona em 401 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private auth: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();
    // Não anexar Authorization para endpoints de autenticação (login/register/refresh)
    // Usar '/api/v1/auth' conforme OpenAPI do backend
    const isAuthEndpoint = req.url.includes('/api/v1/auth');
    const cloned =
      token && !isAuthEndpoint
        ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
        : req;

    return next.handle(cloned).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          // if this was a refresh request or auth endpoints, don't attempt to refresh
          if (
            req.url.includes('/api/v1/auth/refresh') ||
            req.url.includes('/api/v1/auth/login') ||
            req.url.includes('/api/v1/auth/register')
          ) {
            this.auth.logout();
            return throwError(() => err);
          }

          // try refresh
          return this.auth.refreshToken().pipe(
            switchMap((newToken) => {
              if (newToken) {
                const retryReq = req.clone({
                  setHeaders: { Authorization: `Bearer ${newToken}` },
                });
                return next.handle(retryReq);
              }
              // unable to refresh -> logout
              this.auth.logout();
              return throwError(() => err);
            }),
            catchError((e) => {
              this.auth.logout();
              return throwError(() => e);
            })
          );
        }
        return throwError(() => err);
      })
    );
  }
}
