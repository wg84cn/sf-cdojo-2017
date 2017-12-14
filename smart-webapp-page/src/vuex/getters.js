const getters = {
  diction: state => state.app.diction,
  serverTypeOptions: state => state.app.serverTypeOptions,
  dbTypeOptions: state => state.app.dbTypeOptions,
  onlineDays: state => state.app.onlineDays,
  testVersions: state => state.app.testVersions,
  osTypeOptions: state => state.app.osTypeOptions,
};
export default getters
