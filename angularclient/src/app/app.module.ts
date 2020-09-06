import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AddMitarbeiterFormComponent} from './components/mitarbeiter/add-mitarbeiter-form.component';
import {MitarbeiterService} from "./services/mitarbeiter.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {TableMitarbeiterComponent} from './components/mitarbeiter/table-mitarbeiter.component';
import {SearchMitarbeiterComponent} from './components/mitarbeiter/search-mitarbeiter.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatTableModule} from "@angular/material/table";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {UpdateMitarbeiterFormComponent} from './components/mitarbeiter/update-mitarbeiter-form.component';
import {MatDividerModule} from "@angular/material/divider";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {TableVertriebComponent} from './components/vertrieb/table-vertrieb.component';
import {AddVertriebComponent} from './components/vertrieb/add-vertrieb.component';
import {UpdateVertriebComponent} from './components/vertrieb/update-vertrieb.component';
import {UpdateEinsatzComponent} from './components/einsatz/update-einsatz.component';
import {SearchEinsatzComponent} from './components/einsatz/search-einsatz.component';
import {TableEinsatzComponent} from './components/einsatz/table-einsatz.component';
import {AddEinsatzComponent} from './components/einsatz/add-einsatz.component';
import {MatDatepickerModule} from "@angular/material/datepicker";

@NgModule({
  declarations: [
    AppComponent,
    AddMitarbeiterFormComponent,
    TableMitarbeiterComponent,
    SearchMitarbeiterComponent,
    UpdateMitarbeiterFormComponent,
    TableVertriebComponent,
    AddVertriebComponent,
    UpdateVertriebComponent,
    UpdateEinsatzComponent,
    SearchEinsatzComponent,
    TableEinsatzComponent,
    AddEinsatzComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NoopAnimationsModule,
    MatCardModule,
    MatButtonModule,
    MatMenuModule,
    MatTableModule,
    MatFormFieldModule,
    MatSortModule,
    MatPaginatorModule,
    MatInputModule,
    ReactiveFormsModule,
    MatOptionModule,
    MatSelectModule,
    MatDividerModule,
    MatSidenavModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [MitarbeiterService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
