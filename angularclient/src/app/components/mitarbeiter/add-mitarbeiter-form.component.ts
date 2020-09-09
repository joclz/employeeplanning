import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {MitarbeiterUnit} from "../../models/mitarbeiter/mitarbeiter-unit.enum";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-add-mitarbeiter-form',
  templateUrl: './add-mitarbeiter-form.component.html',
  styleUrls: ['./add-mitarbeiter-form.component.css']
})
export class AddMitarbeiterFormComponent implements OnInit, OnDestroy {

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  id = new FormControl();
  name = new FormControl('', [Validators.required, patternNames]);
  vorname = new FormControl('', [Validators.required, patternNames]);
  stundensatz = new FormControl('', [Validators.required]);
  status = new FormControl('', [Validators.required]);
  unit = new FormControl('', [Validators.required]);

  mitarbeiterUnit = MitarbeiterUnit;
  mitarbeiterUnits = [];

  mitarbeiterStatus = MitarbeiterStatus;
  mitarbeiterStatusList = [];

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
    this.mitarbeiterUnits = Object.keys(this.mitarbeiterUnit);
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatus);
  }

  onSubmit() {
    let mitarbeiter = new Mitarbeiter();
    mitarbeiter.name = this.name.value;
    mitarbeiter.vorname = this.vorname.value;
    mitarbeiter.stundensatzEK = this.stundensatz.value;
    mitarbeiter.mitarbeiterStatus = <MitarbeiterStatus>this.status.value;
    mitarbeiter.mitarbeiterUnit = <MitarbeiterUnit>this.unit.value;

    this.mitarbeiterService.save(mitarbeiter).subscribe(() =>
      this.formRef.resetForm()
    );
  }

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      name: this.name,
      vorname: this.vorname,
      stundensatz: this.stundensatz,
      status: this.status,
      unit: this.unit
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }

}

