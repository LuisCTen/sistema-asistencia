import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
// 👇 Importamos las interfaces desde Core
import { UserRequest, UserResponse } from 'src/app/core/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  // URL base: http://localhost:8080/api/usuarios
  private readonly URL = `${environment.api}/api/usuarios`;

  constructor(private http: HttpClient) {}

  // 1. Listar Todos
  getAll(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(this.URL);
  }

  // 2. Obtener por ID
  getById(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.URL}/${id}`);
  }

  // 3. Crear Usuario
  create(user: UserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(this.URL, user);
  }

  // 4. Actualizar Usuario
  update(id: number, user: UserRequest): Observable<UserResponse> {
    return this.http.put<UserResponse>(`${this.URL}/${id}`, user);
  }

  // 5. Eliminar (Dar de baja)
  delete(id: number): Observable<UserResponse> {
    return this.http.delete<UserResponse>(`${this.URL}/${id}`);
  }
}
