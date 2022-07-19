export interface Company {
    id: number;
    name: string;
    companyRegNumber: string;
    paymentMethod: string;
    description: string;
    events: Event[];
}