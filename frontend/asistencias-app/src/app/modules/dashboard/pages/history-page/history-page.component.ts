import { Component } from '@angular/core';
import { HistorialResponse } from 'src/app/core/models/historial-response.model';
import { AsistenciaService } from '../../services/asistencia.service';

@Component({
  selector: 'app-history-page',
  templateUrl: './history-page.component.html',
  styleUrls: ['./history-page.component.css'],
})
export class HistoryPageComponent {
  //Aqui estan los datos
  historial: HistorialResponse[] = [];

  //Paginacion
  totalRegistros: number = 0;
  paginaActual: number = 0;
  tamanoPagina: number = 5; //Seteamos por defecto a 5

  //Filtros (String YYYY--MM--DD)
  filtroInicio: string = '';
  filtroFin: string = '';

  cargando: boolean = false;

  constructor(private asistenciaService: AsistenciaService) {}

  ngOnInit(): void {this.cargarDatos();}

  cargarDatos() {
    this.cargando = true;

    //Llamando al servicio
    this.asistenciaService
      .obtenerHistorial(
        this.paginaActual,
        this.tamanoPagina,
        this.filtroInicio || undefined, //si viene vacio envia el undefined
        this.filtroFin || undefined
      )
      .subscribe({
        next: (data) => {
          this.historial = data.content;
          this.totalRegistros = data.totalElements;
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error cargando historial', err);
          this.cargando = false;
        },
      });
  }

  cambiarPagina(incremento:number){
    const nuevaPagina = this.paginaActual+incremento;

    //Antes se debe validar
    if(nuevaPagina<0) return;
    if(nuevaPagina *this.tamanoPagina>=this.totalRegistros) return;

    this.paginaActual=nuevaPagina;
    this.cargarDatos();
  }

  aplicarFiltros(){
    this.paginaActual=0;//Reinicia a la primera pagina
    this.cargarDatos();
  }

  limpiarFiltros(){
    this.filtroInicio='';
    this.filtroFin='';
    this.paginaActual=0;
    this.cargarDatos();
  }

}
