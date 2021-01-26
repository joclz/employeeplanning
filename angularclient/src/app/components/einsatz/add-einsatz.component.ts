import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzService} from "../../services/einsatz/einsatz.service";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {MitarbeiterVertriebService} from "../../services/vertrieb/mitarbeiter-vertrieb.service";
import {EinsatzDTO} from "../../models/einsatz/einsatz-dto";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";

@Component({
  selector: 'app-add-einsatz',
  templateUrl: './add-einsatz.component.html',
  styleUrls: ['./add-einsatz.component.css']
})
export class AddEinsatzComponent implements OnInit {

  @ViewChild(FormGroupDirective) formRef: FormGroupDirective;

  formGroup: FormGroup;

  mitarbeiter = new FormControl('', [Validators.required]);
  mitarbeiterVertrieb = new FormControl('', [Validators.required]);
  einsatzStatus = new FormControl('', [Validators.required]);
  beginn = new FormControl('', [Validators.required]);
  ende = new FormControl('', [Validators.required]);
  wahrscheinlichkeit = new FormControl('', [Validators.required, Validators.pattern('^[0-9]+$')]);
  zusatzkostenReise = new FormControl('', [Validators.required, Validators.pattern('^[0-9]+([\\.][0-9]+)?$')]);
  stundensatzVK = new FormControl('', [Validators.required, Validators.pattern('^[0-9]+([\\.][0-9]+)?$')]);
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
    let einsatzDTO = new EinsatzDTO();
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

    this.einsatzService.save(einsatzDTO).subscribe(() => {
      this.formRef.resetForm()
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
    });

    this.mitarbeiterVertriebService.getMitarbeiterVertriebListOrderByName().subscribe(result => {
      this.mitarbeiterVertriebList = result;
    });

    this.formGroup = new FormGroup({
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
