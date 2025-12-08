import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class InjectSessionInterceptor implements HttpInterceptor {
  constructor(private cookieService: CookieService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    try {
      //Obtener token(y debe ser el mismo lugar donde lo guardo AuthService)
      const token = this.cookieService.get('token'); //

      let newRequest = request;

      if (token) {
        newRequest = request.clone({
          setHeaders: {
            //Este es lo que espera Spring Security(JwtAutheticationFilter)
            authorization: `Bearer ${token}`,
          },
        });
      }

      //Aqui entonces continuamos con la peticion modificada
      return next.handle(newRequest);
    } catch (e) {
      console.error('Error interceptando token', e);

      //Si no hay token enviamos la peticion original
      return next.handle(request);
    }
  }
}
