import React, { Component, PropTypes } from 'react';
var { requireNativeComponent } = require('react-native');

var RCTAgora = requireNativeComponent('RCTAgora', Agora, {
    nativeOnly: {onChange: true}
});

export default class Agora extends Component {
    static  propTypes = {
        localLeft: PropTypes.number,
        localTop: PropTypes.number,
        localWidth: PropTypes.number,
        localHeight: PropTypes.number,
    }

    constructor(props) {
        super(props);
        console.log("constructor=====", Agora);
    }

    render() {
        return <RCTAgora {...this.props} />
    }
}