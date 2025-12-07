import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HeaderUserComponent } from './components/header-user/header-user.component';
import { RouterModule } from '@angular/router'; // routerLink

@NgModule({
  declarations: [SidebarComponent, HeaderUserComponent],
  imports: [
    CommonModule,
    RouterModule, // No olvidar que es necesario para que funcione el menú
  ],
  exports: [SidebarComponent, HeaderUserComponent],
})
export class SharedModule {}
