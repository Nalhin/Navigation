export const uniqueId = () => {
  let id = 1;
  return { getAndIncrement: () => id++, reset: () => (id = 1) };
};
