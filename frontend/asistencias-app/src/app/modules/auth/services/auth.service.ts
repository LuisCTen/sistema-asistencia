import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly URL = environment.api;
  //Enviar peticion de login a la API -Almacenar token en navegador
  constructor(private http: HttpClient, private cookie: CookieService) {}

  sendCredentials(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.http.post(`${this.URL}/auth/login`, body).pipe(
      tap((response: any) => {
        //Interceptar flujo de Observable
        const { token } = response;
        //Guardamos token y le damos 4 días de validez.
        this.cookie.set('token', token, 4, '/');
        //Guardar Username para mostrar en el Header
        localStorage.setItem('username', username);

        const payloadBase64 = token.split('.')[1];
        const payloadDecoded = atob(payloadBase64); // Decodifica base64 a texto
        const payloadObj = JSON.parse(payloadDecoded); // Convierte texto a JSON objeto
        const rol = payloadObj.roles[0];

        // 3. Guardar el Rol
        localStorage.setItem('rol', rol);
      })
    );
  }

  logout() {
    this.cookie.delete('token', '/');
    localStorage.removeItem('username');
    localStorage.clear();
  }
}
