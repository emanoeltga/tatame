import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-sobre',
  imports: [CommonModule, RouterModule],
  template: `
    <div class="page">
      <h1>SOBRE</h1>
      <p>Placeholder para a página Sobre.</p>
    </div>
  `,
  styles: [
    `
      :host {
        display: block;
        padding: 1rem;
      }
      h1 {
        margin: 0 0 0.5rem 0;
      }
    `,
  ],
})
export class SobreComponent {}
