import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Einsatz} from "../../models/einsatz/einsatz";
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzService} from "../../services/einsatz/einsatz.service";
import {MitarbeiterVertriebService} from "../../services/vertrieb/mitarbeiter-vertrieb.service";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";
import {EinsatzDTO} from "../../models/einsatz/einsatz-dto";

@Component({
  selector: 'app-update-einsatz',
  templateUrl: './update-einsatz.component.html',
  styleUrls: ['./update-einsatz.component.css']
})
export class UpdateEinsatzComponent implements OnInit {

  @Input() einsatzInput: Einsatz;
  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  id = new FormControl({value: '', disabled: true});
  mitarbeiter = new FormControl('', [Validators.required]);
  mitarbeiterVertrieb = new FormControl('', [Validators.required]);
  einsatzStatus = new FormControl('', [Validators.required]);
  beginn = new FormControl('', [Validators.required]);
  ende = new FormControl('', [Validators.required]);

  //TODO Validatoren?
  wahrscheinlichkeit = new FormControl('', [Validators.required]);
  zusatzkostenReise = new FormControl('', [Validators.required]);
  stundensatzVK = new FormControl('', [Validators.required]);
  projektnummerNettime = new FormControl('', [Validators.required]);
  beauftragungsnummer = new FormControl('', [Validators.required]);

  einsatzStatusEnum = EinsatzStatus;
  einsatzStatusList = [];

  mitarbeiterList: Array<MitarbeiterDTO>;

  mitarbeiterVertriebList: Array<MitarbeiterDTO>;

  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router,
              private einsatzService: EinsatzService,
              private mitarbeiterService: MitarbeiterService,
              private mitarbeiterVertriebService: MitarbeiterVertriebService) {
    this.einsatzStatusList = Object.keys(this.einsatzStatusEnum);
  }

  onSubmit() {
    //TODO Weiterleitung nach Update? Oder nur Meldung ausgeben?

    let einsatzDTO = new EinsatzDTO();
    einsatzDTO.id = this.id.value;
    einsatzDTO.mitarbeiterId = this.mitarbeiter.value;
    einsatzDTO.mitarbeiterVertriebId = this.mitarbeiterVertrieb.value;
    einsatzDTO.einsatzStatus = <EinsatzStatus>this.einsatzStatus.value;
    einsatzDTO.beginn = this.beginn.value;
    einsatzDTO.ende = this.ende.value;
    einsatzDTO.wahrscheinlichkeit = this.wahrscheinlichkeit.value;
    einsatzDTO.zusatzkostenReise = this.zusatzkostenReise.value;
    einsatzDTO.stundensatzVK = this.stundensatzVK.value;
    einsatzDTO.projektnummerNettime = this.projektnummerNettime.value;
    einsatzDTO.beauftragungsnummer = this.beauftragungsnummer.value;

    this.einsatzService.update(einsatzDTO).subscribe(data => { 
       this.showSuccessMsg = true;
       this.showErrorMsg = false; 
    }, (err) => {
       this.showSuccessMsg = false;
       this.showErrorMsg = true; 
    });
  }

  ngOnInit(): void {
    this.mitarbeiterService.getMitarbeiterListOrderByName().subscribe(result => {
      this.mitarbeiterList = result;
      this.mitarbeiter.setValue(this.mitarbeiterList.find(x => x.id == this.einsatzInput.mitarbeiter.id).id);
    });
    this.mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName().subscribe(result => {
      this.mitarbeiterVertriebList = result;
      this.mitarbeiterVertrieb.setValue(this.mitarbeiterVertriebList.find(x => x.id == this.einsatzInput.mitarbeiterVertrieb.id).id);
    });

    this.id.setValue(this.einsatzInput.id);
    this.einsatzStatus.setValue(this.einsatzInput.einsatzStatus);
    this.beginn.setValue(this.einsatzInput.beginn);
    this.ende.setValue(this.einsatzInput.ende);
    this.wahrscheinlichkeit.setValue(this.einsatzInput.wahrscheinlichkeit);
    this.zusatzkostenReise.setValue(this.einsatzInput.zusatzkostenReise);
    this.stundensatzVK.setValue(this.einsatzInput.stundensatzVK);
    this.projektnummerNettime.setValue(this.einsatzInput.projektnummerNettime);
    this.beauftragungsnummer.setValue(this.einsatzInput.beauftragungsnummer);

    this.formGroup = new FormGroup({
      id: this.id,
      mitarbeiter: this.mitarbeiter,
      mitarbeiterVertrieb: this.mitarbeiterVertrieb,
      einsatzStatus: this.einsatzStatus,
      beginn: this.beginn,
      ende: this.ende,
      wahrscheinlichkeit: this.wahrscheinlichkeit,
      zusatzkostenReise: this.zusatzkostenReise,
      stundensatzVK: this.stundensatzVK,
      projektnummerNettime: this.projektnummerNettime,
      beauftragungsnummer: this.beauftragungsnummer
    });
  }

  ngOnDestroy(): void {
    this.formRef.resetForm();
  }

}
