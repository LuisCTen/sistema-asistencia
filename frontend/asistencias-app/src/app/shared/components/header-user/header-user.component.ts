import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrls: ['./header-user.component.css'],
})
export class HeaderUserComponent {
  username: string = 'Usuario';
  rol: string = '';

  constructor(private router: Router, private cookieService: CookieService) {}

  ngOnInit(): void {
    // Recuperamos del almacenamiento local
    this.username = localStorage.getItem('username') || 'Invitado';

    this.rol = localStorage.getItem('rol') || '';
  }

  logout() {
    // 1. Limpiar LocalStorage (Rol, Username, etc.)
    localStorage.clear();

    // 2. Limpiar Cookies (Token)
    this.cookieService.delete('token', '/');

    // 3. Redirigir al Login
    this.router.navigate(['/auth/login']);
  }
}
