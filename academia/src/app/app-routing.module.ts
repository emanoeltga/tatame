import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoreAuthGuard } from './core/guards/auth.guard';
import { HomeComponent } from './layout/pages/home/home.component';

const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [CoreAuthGuard] },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./dashboard/dashboard.component').then(
        (m) => m.DashboardComponent
      ),
    canActivate: [CoreAuthGuard],
  },
  {
    path: 'eventos',
    loadComponent: () =>
      import('./eventos/eventos.component').then((m) => m.EventosComponent),
    canActivate: [CoreAuthGuard],
  },
  {
    path: 'sobre',
    loadComponent: () =>
      import('./sobre/sobre.component').then((m) => m.SobreComponent),
    canActivate: [CoreAuthGuard],
  },
  {
    path: 'academias',
    loadComponent: () =>
      import('./academias/academias.component').then(
        (m) => m.AcademiasComponent
      ),
    canActivate: [CoreAuthGuard],
  },
  {
    path: 'cadastros',
    loadComponent: () =>
      import('./cadastros/cadastros.component').then(
        (m) => m.CadastrosComponent
      ),
    canActivate: [CoreAuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'alunos',
    loadChildren: () =>
      import('./features/alunos/alunos.module').then((m) => m.AlunosModule),
  },
  // fallback
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
