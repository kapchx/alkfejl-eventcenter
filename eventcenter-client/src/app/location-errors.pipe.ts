import { Pipe, PipeTransform } from '@angular/core';
import { ValidationErrors } from '@angular/forms';

@Pipe({
  name: 'locationErrors'
})
export class LocationErrorsPipe implements PipeTransform {

  transform(value: ValidationErrors): string {
    if (value.required){
      return 'Location is required.';
    }
    return JSON.stringify(value);
  }

}


