import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'app-login-page',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginPageComponent implements OnInit {
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
      (resp: any) => {
        this.loading = false;
        // não imprimir tokens completos — mostrar apenas confirmação
        console.log('Login success');
        if (this.auth.isAuthenticated()) {
          this.router.navigateByUrl(this.returnUrl ?? '/home');
        } else {
          this.error = 'Token não retornado';
        }
      },
      (err: any) => {
        this.loading = false;
        console.error('Login error', err);
        this.error = err?.error?.message ?? err?.statusText ?? 'Erro';
      }
    );
  }
}
