import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MitarbeiterListComponent} from "./mitarbeiter-list/mitarbeiter-list.component";
import {MitarbeiterFormComponent} from "./mitarbeiter-form/mitarbeiter-form.component";

const routes: Routes = [
  { path: 'mitarbeiter', component: MitarbeiterListComponent },
  { path: 'addMitarbeiter', component: MitarbeiterFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
