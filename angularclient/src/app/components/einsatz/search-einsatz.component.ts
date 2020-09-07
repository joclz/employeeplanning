import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TableMitarbeiterComponent} from "../mitarbeiter/table-mitarbeiter.component";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {MitarbeiterStatus} from "../../models/mitarbeiter-status.enum";

const patternId = Validators.pattern('^\\d+$');

@Component({
  selector: 'app-search-einsatz',
  templateUrl: './search-einsatz.component.html',
  styleUrls: ['./search-einsatz.component.css']
})
export class SearchEinsatzComponent implements OnInit {

  lastEndDate = new FormControl({value: '', disabled: true});
  lastEndDateId = new FormControl('', [Validators.required, patternId]);
  lastEndDateFormGroup: FormGroup;

  chance = new FormControl({value: '', disabled: true});
  chanceId = new FormControl('', [Validators.required, patternId]);
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

  @ViewChild('tableMitarbeiterBank') tableMitarbeiterBank: TableMitarbeiterComponent;
  @ViewChild('tableMitarbeiterInternBank') tableMitarbeiterInternBank: TableMitarbeiterComponent;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
  }

  ngOnInit(): void {
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
        let endDate = new Date(result);
        endDate.setHours(2);
        this.lastEndDate.setValue(endDate.toISOString().slice(0, 16));
      } else {
        this.lastEndDate.reset();
      }
    });
  }

  getChanceOnSubmit() {
    this.mitarbeiterService.getChanceForMitarbeiter(this.chanceId.value)
      .subscribe(result => this.chance.setValue(result.toString()));
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