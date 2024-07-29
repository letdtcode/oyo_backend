import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

export default function PieChart({ data }) {
    const labels = [];
    const value = [];
    if (data) {
        for (var i = 0; i < data.length; i++) {
            labels.push(data[i].accomName);
            value.push(data[i].numberOfBooking);
        }
    }

    const dataShow = {
        labels: labels,
        datasets: [
            {
                label: 'Số lượng đã đặt phòng',
                data: value,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }
        ]
    };

    return <Doughnut data={dataShow} />;
}
