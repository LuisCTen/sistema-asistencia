package com.semillero.asistencias_backend;

import org.hibernate.annotations.Comment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.semillero.asistencias_backend.models.RoleEntity;
import com.semillero.asistencias_backend.models.UserEntity;
import com.semillero.asistencias_backend.repository.IRoleRepository;
import com.semillero.asistencias_backend.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@Component
@RequiredArgsConstructor
public class AsistenciasBackendApplication implements CommandLineRunner{

	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AsistenciasBackendApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
		String usernameAdmin = "adminIndra";
		//Solo insertar si no existe el admin
       if(userRepository.findByUsername(usernameAdmin).isEmpty()){
			System.out.println("No existe admin, se esta creando admin inicial...");
			//Buscar o crear el rol ADMIN
			RoleEntity rolAdmin=roleRepository.findByNombre("ADMIN")
			                                     .orElseGet(()->roleRepository.save(RoleEntity.builder().nombre("ADMIN").build()));
			//Crear usuario admin

			UserEntity admin = UserEntity.builder()
									    .username(usernameAdmin)
			                            .password(passwordEncoder.encode("841606"))
										.nombreCompleto("Administrador del sistema")
										.email("admin@indra.company.com")
										.estado(1)
										.role(rolAdmin)
										.build();
			userRepository.save(admin);
			System.out.println("Se creo el usuario'" + usernameAdmin + "'con clave 841606");
	   }else {
            System.out.println("✅ El usuario '" + usernameAdmin + "' ya existe. No se hace nada.");
        }
    }

}
