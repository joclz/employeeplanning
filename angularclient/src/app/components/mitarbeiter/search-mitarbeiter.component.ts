import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter/mitarbeiter.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {TableMitarbeiterComponent} from "./table-mitarbeiter.component";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";

@Component({
  selector: 'app-search-mitarbeiter',
  templateUrl: './search-mitarbeiter.component.html',
  styleUrls: ['./search-mitarbeiter.component.css']
})
export class SearchMitarbeiterComponent implements OnInit {

  lastEndDate = new FormControl({value: '', disabled: true});
  lastEndDateId = new FormControl('', [Validators.required]);
  lastEndDateFormGroup: FormGroup;

  chance = new FormControl({value: '', disabled: true});
  chanceId = new FormControl('', [Validators.required]);
  chanceFormGroup: FormGroup;

  mitarbeiterEinsatz = new FormControl({value: '', disabled: true});
  mitarbeiterEinsatzFormGroup: FormGroup;

  subunternehmerEinsatz = new FormControl({value: '', disabled: true});
  subunternehmerEinsatzFormGroup: FormGroup;

  deckungsbeitrag = new FormControl({value: '', disabled: true});
  deckungsbeitragFormGroup: FormGroup;

  isMitarbeiterBank = false;
  mitarbeiterBankFormGroup: FormGroup;

  isMitarbeiterInternBank = false;
  mitarbeiterInternBankFormGroup: FormGroup;

  mitarbeiterList: Array<MitarbeiterDTO>;

  @ViewChild('tableMitarbeiterBank') tableMitarbeiterBank: TableMitarbeiterComponent;
  @ViewChild('tableMitarbeiterInternBank') tableMitarbeiterInternBank: TableMitarbeiterComponent;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
  }

  ngOnInit(): void {
    this.mitarbeiterService.getMitarbeiterListOrderByName().subscribe(result => {
      this.mitarbeiterList = result;
    });

    this.lastEndDateFormGroup = new FormGroup({
      id: this.lastEndDateId,
      lastEndDate: this.lastEndDate
    });
    this.chanceFormGroup = new FormGroup({
      id: this.chanceId,
      chance: this.chance
    });
    this.mitarbeiterEinsatzFormGroup = new FormGroup({
      mitarbeiterEinsatz: this.mitarbeiterEinsatz
    });
    this.subunternehmerEinsatzFormGroup = new FormGroup({
      subunternehmerEinsatz: this.subunternehmerEinsatz
    });
    this.deckungsbeitragFormGroup = new FormGroup({
      deckungsbeitrag: this.deckungsbeitrag
    });
    this.mitarbeiterBankFormGroup = new FormGroup({});
    this.mitarbeiterInternBankFormGroup = new FormGroup({});
  }

  getLastEndDateOnSubmit() {
    this.mitarbeiterService.getLastEndDateForMitarbeiter(this.lastEndDateId.value).subscribe(result => {
      if (result) {
        this.lastEndDate.setValue(new Date(result).toLocaleDateString());
      } else {
        this.lastEndDate.setValue("Mitarbeiter ist nicht im Einsatz");
      }
    });
  }

  getChanceOnSubmit() {
    this.mitarbeiterService.getChanceForMitarbeiter(this.chanceId.value)
      .subscribe(result => {
        this.chance.setValue(result.toString())
      });
  }

  getMitarbeiterEinsatzOnSubmit() {
    this.mitarbeiterService.getMitarbeiterImEinsatz(MitarbeiterStatus.ANGESTELLT)
      .subscribe(result => this.mitarbeiterEinsatz.setValue(result.toString()));
  }

  getSubunternehmerEinsatzOnSubmit() {
    this.mitarbeiterService.getMitarbeiterImEinsatz(MitarbeiterStatus.SUBUNTERNEHMER)
      .subscribe(result => this.subunternehmerEinsatz.setValue(result.toString()));
  }

  getDeckungsbeitragOnSubmit() {
    this.mitarbeiterService.getDeckungsbeitrag().subscribe(result => this.deckungsbeitrag.setValue(result.toString()));
  }

  getMitarbeiterBankOnSubmit() {
    this.initMitarbeiterBank();
    this.isMitarbeiterBank = true;
  }

  private initMitarbeiterBank() {
    if (this.tableMitarbeiterBank) {
      this.tableMitarbeiterBank.ngOnInit();
    }
  }

  getMitarbeiterInternBankOnSubmit() {
    this.initMitarbeiterInternBank();
    this.isMitarbeiterInternBank = true;
  }

  private initMitarbeiterInternBank() {
    if (this.tableMitarbeiterInternBank) {
      this.tableMitarbeiterInternBank.ngOnInit();
    }
  }
}
