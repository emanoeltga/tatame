import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss'],
})
export class TopbarComponent implements OnInit, OnDestroy {
  userName: string | null = null;
  open = false;
  visivel = false;
  private sub: Subscription | null = null;

  constructor(private auth: AuthService) {}

  ngOnInit(): void {
    // Assina mudanças reativas no usuário
    this.sub = this.auth.user$.subscribe((name) => {
      this.userName = name;
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  toggle() {
    this.open = !this.open;
  }

  logout() {
    const ok = window.confirm('Deseja realmente encerrar a sessão?');
    if (!ok) return;
    this.auth.logout();
  }
}
