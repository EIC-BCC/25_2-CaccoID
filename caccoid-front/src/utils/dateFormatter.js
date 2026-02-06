export const dateFormatter = (dateString) => {
    const date = new Date(dateString);

    const formattedDate = date.toLocaleDateString('pt-BR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
    });

    return formattedDate;
}