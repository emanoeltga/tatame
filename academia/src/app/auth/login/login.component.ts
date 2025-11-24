import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  // kept templateUrl for readability
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
    `
      .error {
        color: #b00020;
        margin-top: 0.5rem;
      }
      input.invalid {
        border: 1px solid #b00020;
      }
      .field {
        margin-bottom: 0.75rem;
      }
      label {
        display: block;
        font-weight: 600;
        margin-bottom: 0.25rem;
      }
      button[disabled] {
        opacity: 0.6;
      }
    `,
  ],
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  error: string | null = null;
  private returnUrl: string | null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
    // captura returnUrl passado pelo AuthGuard
    this.route.queryParamMap.subscribe((qp) => {
      const u = qp.get('returnUrl');
      this.returnUrl = u && u !== '/' ? u : null;
    });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.error = null;

    this.auth.login(this.form.value).subscribe(
      (resp) => {
        this.loading = false;
        console.log('Login success');
        // somente navega se o token foi salvo e o usuário está autenticado
        if (this.auth.isAuthenticated()) {
          const target = this.returnUrl ?? '/home';
          this.router.navigateByUrl(target);
        } else {
          this.error =
            'Autenticação incompleta: token não recebido do servidor.';
        }
      },
      (err) => {
        this.loading = false;
        console.error('Login error', err);
        const status = err?.status;
        const backendMsg = err?.error?.message || err?.error?.detail;
        const body =
          err?.error && typeof err.error === 'object'
            ? JSON.stringify(err.error)
            : err?.error;
        // log adicional do corpo retornado pelo backend para facilitar depuração (sensíveis redigidos)
        try {
          const redacted =
            typeof body === 'string'
              ? body.replace(
                  /"(jwt|token|accessToken|refreshToken)"\s*:\s*"[^\"]+"/gi,
                  '"$1":"[REDACTED]"'
                )
              : body;
          console.log('Login error body (redacted):', redacted);
        } catch {
          // ignore
        }
        this.error = backendMsg
          ? `${backendMsg}`
          : status
          ? `Erro ${status}: ${err?.statusText || body || 'Não autorizado'}`
          : 'Erro ao autenticar. Verifique suas credenciais.';
      }
    );
  }
}
