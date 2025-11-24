import { Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';

@Injectable({ providedIn: 'root' })
export class AlunosService {
  constructor(private api: ApiService) {}
  list() {
    return this.api.get('/atletas');
  }
}
