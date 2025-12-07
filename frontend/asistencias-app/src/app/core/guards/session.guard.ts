import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

//funcion StandAlone(sessionGuard) que implementa la interfaz CanActivateFn
export const sessionGuard: CanActivateFn = (route, state) => {
  //Usamos inject para obtener la instancia del servicio (CookieService) dentro de un StandAlone
  const cookieService = inject(CookieService); //interactua con las cookies del navegador y verificar el token de sesión.
  const router = inject(Router); //para poder redirigir al usuario a otra página si no tiene acceso

  try{
    const token :boolean =cookieService.check('token');

    if(!token){
      console.warn('No hay una sesión valida, será redigido al login...');
      router.navigate(['/','auth','login']);
      return false;
    }
    return true;

  }catch(error){
    console.error('Error verificando la sesion',error);
    router.navigate(['/','auth','login']);
    return false;
  }

};
