import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  mainMenu:{defaultOptions:Array<any>,accessLink: Array<any>}={defaultOptions:[],accessLink:[]}

  constructor() {}

  ngOnOnit():void{
    this.mainMenu.defaultOptions = [
      {
        name: 'Dashboard',
        icon: 'uil uil-estate',
        router: ['/', 'dashboard'],
      },
      {
        name: 'Historial',
        icon: 'uil uil-history',
        router: ['/', 'history'],
      },
      {
        name: 'Justificaciones',
        icon: 'uil uil-document-info',
        router: ['/', 'justifications'],
      },
    ];
    this.mainMenu.accessLink = [
      {
        name: 'Reportes',
        icon: 'uil uil-analytics',
        router: ['/', 'reports'],
      },
      {
        name:'Cerrar Sesion',
        icon:'uil uil-signout',
        router:['/','auth','login']//Aqui debería logica del logout
      }
    ];
  }
}
