import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

@Injectable()
export class CoreAuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService, private router: Router) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();
    const isAuthEndpoint = req.url.includes('/api/v1/auth');
    const cloned =
      token && !isAuthEndpoint
        ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
        : req;
    return next.handle(cloned).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          this.auth.logout();
        }
        return throwError(() => err);
      })
    );
  }
}

export const CORE_HTTP_INTERCEPTORS = [
  { provide: HTTP_INTERCEPTORS, useClass: CoreAuthInterceptor, multi: true },
];
