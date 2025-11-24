import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-dashboard',
  template: `
    <div class="dashboard">
      <h1>Dashboard</h1>
      <p>Rota protegida — você está autenticado.</p>
    </div>
  `,
  styles: [
    `
      .dashboard {
        padding: 1.5rem;
      }
    `,
  ],
})
export class DashboardComponent {}
