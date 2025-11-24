import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { routes } from './app.routes';
import { CoreAuthInterceptor } from './core/interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // register HttpClient providers so services can inject HttpClient
    importProvidersFrom(HttpClientModule),
    { provide: HTTP_INTERCEPTORS, useClass: CoreAuthInterceptor, multi: true },
  ],
};
