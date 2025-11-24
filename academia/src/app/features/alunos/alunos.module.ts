import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AlunosListComponent } from './list/alunos-list.component';
import { AlunosFormComponent } from './form/alunos-form.component';

@NgModule({
  declarations: [AlunosListComponent, AlunosFormComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      { path: '', component: AlunosListComponent },
      { path: 'novo', component: AlunosFormComponent },
    ]),
  ],
})
export class AlunosModule {}
