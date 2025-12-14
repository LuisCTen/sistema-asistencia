export interface UserRequest {
  username: string;
  password?: string; // Opcional (solo obligatorio al crear)
  email: string;
  nombreCompleto: string;
  idRol: number;
  estado: number; // 1 = Activo, 0 = Inactivo
}


export interface UserResponse {
  idUsuario: number;
  username: string;
  email: string;
  nombreCompleto: string;
  nombreRol: string;
  estado: number;
}
