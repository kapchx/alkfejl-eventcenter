
import { Pipe, PipeTransform } from '@angular/core';
import { ValidationErrors } from '@angular/forms';

@Pipe({
  name: 'titleErrors'
})
export class TitleErrorsPipe implements PipeTransform {

  transform(value: ValidationErrors): string {
    if (value.required){
      return 'This field is required.';
    }
    if (value.minlength){
      return `Title must be at least 
      ${value.minlength.requiredLength}  characters long`;
    }
    return JSON.stringify(value);
  }

}


