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
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {UpdateMitarbeiterFormComponent} from './components/mitarbeiter/update-mitarbeiter-form.component';
import {ListMitarbeiterComponent} from './components/mitarbeiter/list-mitarbeiter.component';

@NgModule({
  declarations: [
    AppComponent,
    AddMitarbeiterFormComponent,
    TableMitarbeiterComponent,
    SearchMitarbeiterComponent,
    UpdateMitarbeiterFormComponent,
    ListMitarbeiterComponent
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
    MatSelectModule
  ],
  providers: [MitarbeiterService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
