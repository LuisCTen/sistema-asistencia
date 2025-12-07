import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth-page',
  templateUrl: './auth-page.component.html',
  styleUrls: ['./auth-page.component.css']
})
export class AuthPageComponent {
  errorLogin:boolean =false;

  formLogin=new FormGroup({
    user:new FormControl('',[Validators.required]),
    password: new FormControl('',[Validators.required,Validators.minLength(5)])
  });

  constructor(private authService:AuthService,private router: Router){}

  sendLogin():void{
    if(this.formLogin.valid){
      const {user,password} =this.formLogin.value;

      this.authService.sendCredentials(user!,password!)
      .subscribe({
        next:(response) =>{
          console.log('Sesion correcta: ',response);
          this.router.navigate(['/']);
        },
        error:(err) =>{
          console.log('Error de login',err);
          this.errorLogin=true;
        }
      });
    }else{
      this.formLogin.markAllAsTouched();
      console.log('Form is invalid');
    }
  }
}
