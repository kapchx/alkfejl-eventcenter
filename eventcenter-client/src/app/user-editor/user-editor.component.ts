import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { UserService } from '../core/user.service';

@Component({
  selector: 'app-user-editor',
  templateUrl: './user-editor.component.html',
  styleUrls: ['./user-editor.component.scss']
})
export class UserEditorComponent implements OnInit {

  userForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
  }

  async submit(): Promise<void> {
    if(this.userForm.valid){ 
        console.log(this.userForm.value)
        await this.userService.editUser(this.userForm.value);
        await this.authService.login(this.userForm.value);
        this.router.navigate(['/Events']);
    }
  }

  async deleteUser(): Promise<void> {
    await this.userService.deleteUser();
  }

}
