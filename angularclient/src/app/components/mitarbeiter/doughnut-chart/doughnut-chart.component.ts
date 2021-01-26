import {Component, Input, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {MitarbeiterEinsatzDate} from "../../../models/mitarbeiterEinsatzDate-data";

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: ['./doughnut-chart.component.css']
})
export class DoughnutChartComponent implements OnInit {

  fontColor = '#CCC';

  @Input()
  public mitarbeiterEinsatzDate: MitarbeiterEinsatzDate;

  public doughnutChartLabels = ['int. MA im Einsatz', 'ext. MA im Einsatz', 'int. MA nicht im Einsatz', 'ext. MA nicht im Einsatz'];
  public doughnutChartData = [20, 25, 5, 3];
  public doughnutChartType = 'doughnut';

  public currentMonth = ["Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez",];

  public doughnutChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      labels: {
        fontColor: this.fontColor
      }
    },
    elements: {
      center: {
        text: 'Monat',
        color: this.fontColor
      }
    }
  };

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.doughnutChartData = [
      Number(this.mitarbeiterEinsatzDate.maIntEinsatz),
      Number(this.mitarbeiterEinsatzDate.maExtEinsatz),
      Number(this.mitarbeiterEinsatzDate.maIntOhneEinsatz),
      Number(this.mitarbeiterEinsatzDate.maExtOhneEinsatz),
    ];

    this.doughnutChartOptions.elements.center.text = this.currentMonth[this.mitarbeiterEinsatzDate.actualMonth];

    Chart.plugins.register({
      beforeDraw: function (chart) {
        let width = chart.width,
          height = chart.height,
          ctx = chart.ctx;
        ctx.restore();
        let fontSize = (height / 10).toFixed(2);
        ctx.font = fontSize + "px Arial";
        // @ts-ignore
        if (chart.config.options.elements.center) {
          // @ts-ignore
          ctx.fillStyle = chart.config.options.elements.center.color;
          // @ts-ignore
          let text = chart.config.options.elements.center.text,
            textX = Math.round((width - ctx.measureText(text).width) / 2),
            textY = height / 11 * 6

          ctx.fillText(text, textX, textY);
        }
        ctx.save();
      }
    });
  }
}
