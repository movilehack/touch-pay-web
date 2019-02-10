import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = fb.group({
      fullname: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', Validators.required],
      cpf: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      birthDate: ['', Validators.required],
      cellphone: ['', Validators.required],
      cep: ['', Validators.required],
      number: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  register() {
    const controls = this.form.controls;
    const datestr = controls.birthDate.value;
    alert(datestr);
    const date =
    new Date(parseInt(datestr.substring(0, 2), 10),
            parseInt(datestr.substring(2, 4), 10) - 1,
            parseInt(datestr.substring(4, 8), 10));

    this.http.post('/api/register', {
      name: controls.fullname.value,
      login: controls.login.value,
      password: controls.password.value,
      cpf: controls.cpf.value,
      email: controls.email.value,
      phone: controls.cellphone.value,
      birthDate: date,
      address: {
        postalCode: controls.cep.value,
        number: controls.number.value
      }
    }).subscribe(x => {
      console.log(x);
    });
  }
}
