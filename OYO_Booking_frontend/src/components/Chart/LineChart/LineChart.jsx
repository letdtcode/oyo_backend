import { Line } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    LineElement,
    Legend,
    CategoryScale,
    LinearScale,
    PointElement,
    Filler
} from 'chart.js';
ChartJS.register(Title, Tooltip, LineElement, Legend, CategoryScale, LinearScale, PointElement, Filler);

function LineChart({data}) {
    const dataLine = {
        labels: ['Jan', 'Feb', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'Oct', 'Nov', 'Dec'],
        datasets: [
            {
                label: 'Doanh thu theo tháng',
                data:
                    data !== undefined
                        ? [
                              data[0].amount,
                              data[1].amount,
                              data[2].amount,
                              data[3].amount,
                              data[4].amount,
                              data[5].amount,
                              data[6].amount,
                              data[7].amount,
                              data[8].amount,
                              data[9].amount,
                              data[10].amount,
                              data[11].amount
                          ]
                        : [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                backgroundColor: '#64b5f6',
                borderColor: 'green',
                tension: 0.4,
                fill: true,
                pointStyle: 'rect',
                pointBorderColor: 'blue',
                pointBackgroundColor: '#fff',
                showLine: true
            }
        ]
    };
    return (
        <div>
            <Line data={dataLine}>Hello</Line>
        </div>
    );
}

export default LineChart;
