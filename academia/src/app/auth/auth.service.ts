import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap, map, catchError } from 'rxjs/operators';
import { Observable, of, BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private TOKEN_KEY = 'auth_token';
  private REFRESH_KEY = 'refresh_token';
  private userSubject = new BehaviorSubject<string | null>(this.getUserName());
  public user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  private setTokens(token?: string | null, refresh?: string | null) {
    if (token) localStorage.setItem(this.TOKEN_KEY, token);
    else localStorage.removeItem(this.TOKEN_KEY);

    if (refresh) localStorage.setItem(this.REFRESH_KEY, refresh);
    else localStorage.removeItem(this.REFRESH_KEY);
    // Atualiza o estado reativo do usuário sempre que os tokens mudam
    this.userSubject.next(this.getUserName());
  }

  login(credentials: { email: string; password: string }) {
    return this.http
      .post<any>('http://localhost:8080/api/v1/auth/login', credentials)
      .pipe(
        tap((resp) => {
          // log da resposta do backend para depuração (sensíveis redigidos)
          try {
            const redacted: any = { ...resp };
            delete redacted.jwt;
            delete redacted.token;
            delete redacted.accessToken;
            delete redacted.refreshToken;
            delete redacted.refresh;
            console.log('AuthService.login response (redacted):', redacted);
          } catch {}
          if (!resp) return;
          const token = resp.jwt ?? resp.token ?? resp.accessToken ?? null;
          const refresh = resp.refreshToken ?? resp.refresh ?? null;
          this.setTokens(token, refresh);
          // If backend returned user object, prefer that name (avoids relying solely on JWT payload)
          const backendName = resp?.user?.name ?? resp?.user?.username ?? null;
          if (backendName) {
            this.userSubject.next(backendName);
          }
        })
      );
  }

  register(data: { name?: string; email: string; password: string }) {
    // Endpoint conforme OpenAPI: /api/v1/auth/register/register
    return this.http.post('http://localhost:8080/api/v1/auth/register', data);
  }

  logout() {
    this.setTokens(null, null);
    this.router.navigate(['/auth/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_KEY);
  }

  private parseJwt(token: string): any | null {
    try {
      const payload = token.split('.')[1];
      const decoded = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
      return JSON.parse(decoded);
    } catch {
      return null;
    }
  }

  /**
   * Retorna o payload do JWT armazenado (ou null).
   */
  getUser(): any | null {
    const t = this.getToken();
    if (!t) return null;
    return this.parseJwt(t);
  }

  /**
   * Retorna um nome/identificador amigável para exibição no UI.
   */
  getUserName(): string | null {
    const payload = this.getUser();
    if (!payload) return null;
    // Tenta campos comuns: name, preferred_username, email, sub
    return (
      payload.name ??
      payload.preferred_username ??
      payload.email ??
      payload.sub ??
      null
    );
  }

  isTokenExpired(token?: string): boolean {
    const t = token ?? this.getToken();
    if (!t) return true;
    const payload = this.parseJwt(t);
    // If payload missing or has no exp claim, consider token as NOT expired.
    // Some backends issue JWTs without `exp` (valid until revoked server-side).
    if (!payload) return true;
    if (!payload.exp) return false;
    const expMs = payload.exp * 1000;
    return Date.now() > expMs;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token && !this.isTokenExpired(token);
  }

  /**
   * Tenta renovar token usando refresh token. Retorna observable com novo token.
   */
  refreshToken(): Observable<string | null> {
    // O backend atual (Swagger) não expõe endpoint de refresh.
    // Portanto retornamos `of(null)` e não tentamos renovar.
    return of(null);
  }

  /**
   * Valida o token atual consultando um endpoint leve protegido.
   * Retorna `true` se o token for válido, `false` caso contrário.
   */
  validateToken(): Observable<boolean> {
    const token = this.getToken();
    if (!token) return of(false);
    // endpoint leve que exige autenticação — expõe /status no backend
    return this.http
      .get('http://localhost:8080/status', { responseType: 'text' })
      .pipe(
        map(() => true),
        catchError(() => of(false))
      );
  }
}
