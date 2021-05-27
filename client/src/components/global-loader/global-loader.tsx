import React from 'react';
import { LinearProgress } from '@material-ui/core';
import { useIsFetching, useIsMutating } from 'react-query';
import styled from '@emotion/styled';

const StyledLinearProgress = styled(LinearProgress)`
  position: absolute;
  top: 0;
  z-index: 1000;
  width: 100%;
`;

const GlobalLoader = () => {
  const isFetching = useIsFetching();
  const isMutating = useIsMutating();

  if (!isFetching && !isMutating) {
    return null;
  }

  return (
    <StyledLinearProgress
      variant="indeterminate"
      color="secondary"
      aria-label="loader"
    />
  );
};

export default GlobalLoader;
