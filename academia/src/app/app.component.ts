import { Component, OnInit } from '@angular/core';
import {
  Router,
  Event,
  NavigationStart,
  NavigationEnd,
  NavigationError,
  NavigationCancel,
} from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './auth/auth.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'academia';
  public user = new BehaviorSubject<string | null>(null);

  constructor(private auth: AuthService, private router: Router) {
    this.auth.user$.subscribe((user) => this.user.next(user));
  }

  items: { title: string; link: string }[] = [
    { title: 'BJJ-MT', link: 'https://bjjmt.com.br' },
  ];

  trackByTitle(_index: number, item: { title: string; link: string }) {
    return item.title;
  }

  ngOnInit(): void {
    // Ao carregar a aplicação, redireciona para login se não autenticado
    // Não forçar navegação manual para evitar conflitos com a configuração de rotas.
    // A navegação inicial será tratada pelo roteador com o redirect configurado em AppRoutingModule.
    // Log do token e estado de autenticação para diagnóstico
    console.log(
      '[Auth] hasToken=',
      !!this.auth.getToken(),
      'isAuthenticated=',
      this.auth.isAuthenticated()
    );

    // Se existe token local, valida com backend (evita aceitar token na blacklist)
    const token = this.auth.getToken();
    if (token) {
      this.auth.validateToken().subscribe((valid) => {
        console.log('[Auth] token valid=', valid);
        if (!valid) {
          // token inválido/na blacklist: força logout
          this.auth.logout();
        }
      });
    } else {
      // Sem token local: navegar para login
      if (!this.router.url.startsWith('/auth')) {
        this.router.navigate(['/auth/login'], { replaceUrl: true });
      }
    }

    // Log de eventos do router para ajudar no debug de rotas
    this.router.events.subscribe((e: Event) => {
      if (e instanceof NavigationStart) {
        console.log('[Router] NavigationStart:', e.url);
      } else if (e instanceof NavigationEnd) {
        console.log('[Router] NavigationEnd:', e.urlAfterRedirects);
      } else if (e instanceof NavigationCancel) {
        console.warn('[Router] NavigationCancel:', e);
      } else if (e instanceof NavigationError) {
        console.error('[Router] NavigationError:', e.error);
      }
    });
  }
}
