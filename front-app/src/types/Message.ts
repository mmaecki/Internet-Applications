export interface Message{
    messageId: number;
    from: string;
    to: string;
    subject: string;
    body: string;
    timestamp: Date;
    packageId: number,
    previousMessageId: number;
}
export interface CreateMessage{
    from: string;
    to: string;
    subject: string;
    body: string;
    timestamp: Date;
    packageId: number,
    previousMessageId: number;
}