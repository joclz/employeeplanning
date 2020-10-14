import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {MitarbeiterUnit} from "../../models/mitarbeiter/mitarbeiter-unit.enum";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

const patternNames = Validators.pattern('[a-zA-Z_äÄöÖüÜß\-]*');

@Component({
  selector: 'app-update-mitarbeiter-form',
  templateUrl: './update-mitarbeiter-form.component.html',
  styleUrls: ['./update-mitarbeiter-form.component.css']
})
export class UpdateMitarbeiterFormComponent implements OnInit, OnDestroy {

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  @Input()
  mitarbeiterInput: Mitarbeiter;

  formGroup: FormGroup;

  id = new FormControl({value: '', disabled: true});
  name = new FormControl('', [Validators.required, patternNames]);
  vorname = new FormControl('', [Validators.required, patternNames]);
  stundensatz = new FormControl('', [Validators.required]);
  status = new FormControl('', [Validators.required]);
  unit = new FormControl('', [Validators.required]);

  mitarbeiterUnit = MitarbeiterUnit;
  mitarbeiterUnits = [];

  mitarbeiterStatus = MitarbeiterStatus;
  mitarbeiterStatusList = [];

  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
    this.mitarbeiterUnits = Object.keys(this.mitarbeiterUnit);
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatus);
  }

  onSubmit() {
    let mitarbeiter = new Mitarbeiter();
    mitarbeiter.id = this.id.value;
    mitarbeiter.name = this.name.value;
    mitarbeiter.vorname = this.vorname.value;
    mitarbeiter.stundensatzEK = this.stundensatz.value;
    mitarbeiter.mitarbeiterStatus = <MitarbeiterStatus>this.status.value;
    mitarbeiter.mitarbeiterUnit = <MitarbeiterUnit>this.unit.value;

    this.mitarbeiterService.update(mitarbeiter).subscribe(() => {
       this.showSuccessMsg = true;
       this.showErrorMsg = false;
    }, () => {
       this.showSuccessMsg = false;
       this.showErrorMsg = true;
    });
  }

  ngOnInit(): void {
    this.id.setValue(this.mitarbeiterInput.id);
    this.name.setValue(this.mitarbeiterInput.name);
    this.vorname.setValue(this.mitarbeiterInput.vorname);
    this.stundensatz.setValue(this.mitarbeiterInput.stundensatzEK);
    this.status.setValue(this.mitarbeiterInput.mitarbeiterStatus);
    this.unit.setValue(this.mitarbeiterInput.mitarbeiterUnit);

    this.formGroup = new FormGroup({
      id: this.id,
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
