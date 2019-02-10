import { Component, OnInit, ViewEncapsulation, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.Emulated
})
export class LoginComponent implements OnInit {

  private formGroup: FormGroup;

  constructor(private loginService: LoginService, private fb: FormBuilder, private router: Router) {
    this.formGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  ngOnInit() {
  }

  login() {
    this.lock();
    this.loginService.login(this.formGroup.controls.email.value, this.formGroup.controls.password.value).subscribe(
      response => {
        console.log(response);
        //this.router.navigate(['dashboard']);
        this.unlock();
      },
      err => {
        this.unlock();
      }
    );
  }

  private lock() {
    this.formGroup.controls.email.disable();
    this.formGroup.controls.password.disable();
  }

  private unlock() {
    this.formGroup.controls.email.enable();
    this.formGroup.controls.password.enable();
  }
}
