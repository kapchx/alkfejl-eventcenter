import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { UserService } from '../core/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  userForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  get username(): AbstractControl {
    return this.userForm.get('username') as AbstractControl;
  }

  get password(): AbstractControl {
    return this.userForm.get('password') as AbstractControl;
  }

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  async submit(): Promise<void> {
    if(this.userForm.valid){ 
        await this.userService.createUser(this.userForm.value);
    }
  }

}
