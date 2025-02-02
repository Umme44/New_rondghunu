import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeePinConfiguration } from '../employee-pin-configuration.model';
import { EmployeePinConfigurationService } from '../service/employee-pin-configuration.service';

@Injectable({ providedIn: 'root' })
export class EmployeePinConfigurationRoutingResolveService implements Resolve<IEmployeePinConfiguration | null> {
  constructor(protected service: EmployeePinConfigurationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeePinConfiguration | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employeePinConfiguration: HttpResponse<IEmployeePinConfiguration>) => {
          if (employeePinConfiguration.body) {
            return of(employeePinConfiguration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
