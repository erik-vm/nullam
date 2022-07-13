export interface Event {
    id: number;
    name: string;
    time: Date;
    location: string;
    description: string;
    personParticipants: any[];
    companyParticipants: any[];
}