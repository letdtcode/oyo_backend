export const getFileExtension = (fileName) => {
    const parts = fileName.split('.');
    return parts[parts.length - 1];
};
