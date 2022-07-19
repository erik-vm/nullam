import { Company } from "./company.interface";
import { Person } from "./person.interface";

export interface Event {
    id: number;
    name: string;
    time: Date;
    location: string;
    description: string;
    totalParticipants: number;
    personParticipants: Person[];
    companyParticipants: Company[];
}