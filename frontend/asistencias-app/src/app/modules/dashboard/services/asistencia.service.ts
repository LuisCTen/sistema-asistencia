import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AsistenciaResponse } from 'src/app/core/models/asistencia-response.model';
import { HistorialResponse } from 'src/app/core/models/historial-response.model';
import { Page } from 'src/app/core/models/page.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',//Singleton(disponible en toda la app)
})
export class AsistenciaService {
  private readonly URL = environment.api; //localhost:8080

  constructor(private http: HttpClient) {}//Inyecccion para realizar las peticiones

  registrarEntrada(): Observable<AsistenciaResponse> {
    return this.http.post<AsistenciaResponse>(
      `${this.URL}/asistencias/marcar-asistencia`,
      {}
    );
  }

  registrarSalida(): Observable<AsistenciaResponse> {
    return this.http.post<AsistenciaResponse>(
      `${this.URL}/asistencias/marcar-salida`,
      {}
    );
  }
  //--------------------------------------------------------------------------------------//
  obtenerHistorial(page:number =0,size:number=5,fecInicio?:string,fecFin?:string):
        Observable<Page<HistorialResponse>>{

          let params = new HttpParams().set('page',page.toString())
                                       .set('size',size.toString());

        if(fecInicio) params = params.set('fechaInicio', fecInicio);
        if(fecFin) params = params.set('fechaFin', fecFin);

        return this.http.get<Page<HistorialResponse>>(`${this.URL}/asistencias/historial`,{params});

  }
}
