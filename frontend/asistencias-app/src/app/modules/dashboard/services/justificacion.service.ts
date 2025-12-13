import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JustificacionRequest, JustificacionResponse } from 'src/app/core/models/justificacion.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JustificacionService {

  private readonly URL =environment.api;

  constructor(private http:HttpClient) {}

  registrarJustificacion(request:JustificacionRequest):Observable<JustificacionResponse>{
    return this.http.post<JustificacionResponse>(
     `${this.URL}/justificaciones/registrar`,
     request
    );
  }
}
