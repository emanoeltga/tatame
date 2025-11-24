import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  selector: 'app-register',
  templateUrl: './register.component.html',
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
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.error = null;

    this.auth.register(this.form.value).subscribe(
      () => {
        this.loading = false;
        this.router.navigate(['/login']);
      },
      (err) => {
        this.loading = false;
        const backendMsg = err?.error?.message || err?.error?.detail;
        this.error =
          backendMsg ||
          err?.statusText ||
          'Erro ao registrar. Tente novamente.';
      }
    );
  }
}
