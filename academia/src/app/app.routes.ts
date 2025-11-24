import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadComponent: () =>
      import('./home/home.component').then((m) => m.HomeComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./dashboard/dashboard.component').then(
        (m) => m.DashboardComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'eventos',
    loadComponent: () =>
      import('./eventos/eventos.component').then((m) => m.EventosComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'sobre',
    loadComponent: () =>
      import('./sobre/sobre.component').then((m) => m.SobreComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'academias',
    loadComponent: () =>
      import('./academias/academias.component').then(
        (m) => m.AcademiasComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'cadastros',
    loadComponent: () =>
      import('./cadastros/cadastros.component').then(
        (m) => m.CadastrosComponent
      ),
    canActivate: [AuthGuard],
  },
  // other app routes can be added here
];
